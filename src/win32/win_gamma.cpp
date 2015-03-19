/*
** WIN_GAMMA.C
*/
#include <assert.h>
#include "../renderer/tr_local.h"
#include "../qcommon/qcommon.h"
#include "glw_win.h"
#include "win_local.h"

static unsigned short s_oldHardwareGamma[3][256];

/*
** WG_CheckHardwareGamma
**
** Determines if the underlying hardware supports the Win32 gamma correction API.
*/
void WG_CheckHardwareGamma( void )
{
	HDC			hDC;

	glConfig.deviceSupportsGamma = qfalse;

	// ouned: gamma correction
	// for the fallback, always check if it will work
	if ( r_gammamethod->integer >= GAMMA_HARDWARE ) {
		hDC = GetDC( GetDesktopWindow() );
		glConfig.deviceSupportsGamma = (qboolean)GetDeviceGammaRamp( hDC, s_oldHardwareGamma );
		ReleaseDC( GetDesktopWindow(), hDC );

		if ( glConfig.deviceSupportsGamma )
		{
			//
			// do a sanity check on the gamma values
			//
			if ( ( HIBYTE( s_oldHardwareGamma[0][255] ) <= HIBYTE( s_oldHardwareGamma[0][0] ) ) ||
				 ( HIBYTE( s_oldHardwareGamma[1][255] ) <= HIBYTE( s_oldHardwareGamma[1][0] ) ) ||
				 ( HIBYTE( s_oldHardwareGamma[2][255] ) <= HIBYTE( s_oldHardwareGamma[2][0] ) ) )
			{
				glConfig.deviceSupportsGamma = qfalse;
				ri.Printf( PRINT_WARNING, "WARNING: device has broken gamma support, generated gamma.dat\n" );
			}

			//
			// make sure that we didn't have a prior crash in the game, and if so we need to
			// restore the gamma values to at least a linear value
			//
			if ( ( HIBYTE( s_oldHardwareGamma[0][181] ) == 255 ) )
			{
				int g;

				ri.Printf( PRINT_WARNING, "WARNING: suspicious gamma tables, using linear ramp for restoration\n" );

				for ( g = 0; g < 255; g++ )
				{
					s_oldHardwareGamma[0][g] = g << 8;
					s_oldHardwareGamma[1][g] = g << 8;
					s_oldHardwareGamma[2][g] = g << 8;
				}
			}
		}
	}
}

/*
** GLimp_SetGamma
**
** This routine should only be called if glConfig.deviceSupportsGamma is TRUE
*/
void GLimp_SetGamma( unsigned char red[256], unsigned char green[256], unsigned char blue[256] ) {
	unsigned short table[3][256];
	int		i, j;
	int		ret;
	OSVERSIONINFO	vinfo;

	if ( !glConfig.deviceSupportsGamma || r_gammamethod->integer != GAMMA_HARDWARE || !glw_state.hDC ) {
		return;
	}

	for ( i = 0; i < 256; i++ ) {
		table[0][i] = ( ( ( unsigned short ) red[i] ) << 8 ) | red[i];
		table[1][i] = ( ( ( unsigned short ) green[i] ) << 8 ) | green[i];
		table[2][i] = ( ( ( unsigned short ) blue[i] ) << 8 ) | blue[i];
	}

	// Win2K puts this odd restriction on gamma ramps...
	// ouned: https://github.com/JACoders/OpenJK/commit/43322f7f25dfce966bdbed7376c698cddf796284
	vinfo.dwOSVersionInfoSize = sizeof(vinfo);
	GetVersionEx( &vinfo );
	if ( vinfo.dwMajorVersion >= 5 && vinfo.dwPlatformId == VER_PLATFORM_WIN32_NT ) {
		Com_DPrintf( "performing W2K gamma clamp.\n" );
		for ( j = 0 ; j < 3 ; j++ ) {
			for ( i = 0 ; i < 128 ; i++ ) {
				if ( table[j][i] > ( (128+i) << 8 ) ) {
					table[j][i] = (128+i) << 8;
				}
			}
			if ( table[j][127] > 254<<8 ) {
				table[j][127] = 254<<8;
			}
		}
	} else {
		Com_DPrintf( "skipping gamma clamp.\n" );
	}

	// enforce constantly increasing
	for ( j = 0 ; j < 3 ; j++ ) {
		for ( i = 1 ; i < 256 ; i++ ) {
			if ( table[j][i] < table[j][i-1] ) {
				table[j][i] = table[j][i-1];
			}
		}
	}

	ret = SetDeviceGammaRamp( glw_state.hDC, table );
	if ( !ret ) {
		Com_Printf( "SetDeviceGammaRamp failed.\n" );
	}
}

/*
** WG_RestoreGamma
*/
void WG_RestoreGamma( void ) {
	// ouned: gamma correction
	if (r_gammamethod->integer != GAMMA_HARDWARE) {
		return;
	}

	if ( glConfig.deviceSupportsGamma )
	{
		HDC hDC;
		
		hDC = GetDC( GetDesktopWindow() );
		SetDeviceGammaRamp( hDC, s_oldHardwareGamma );
		ReleaseDC( GetDesktopWindow(), hDC );
	}
}

