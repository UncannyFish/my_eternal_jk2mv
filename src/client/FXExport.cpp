#include "client.h"
#include "FxScheduler.h"


int	FX_RegisterEffect(const char *file)
{
	return theFxScheduler.RegisterEffect(file, true);
}

void FX_PlaySimpleEffect( const char *file, const vec3_t org )
{
	theFxScheduler.PlayEffect(file, org);
}

void FX_PlayEffect( const char *file, const vec3_t org, const vec3_t fwd )
{
	theFxScheduler.PlayEffect(file, org, fwd);
}

void FX_PlayEntityEffect( const char *file, const vec3_t org,
						const vec3_t axis[3], const int boltInfo, const int entNum )
{
	theFxScheduler.PlayEffect(file, org, axis, boltInfo, entNum);
}

void FX_PlaySimpleEffectID( int id, const vec3_t org )
{
	theFxScheduler.PlayEffect(id, org);
}

void FX_PlayEffectID( int id, const vec3_t org, const vec3_t fwd )
{
	theFxScheduler.PlayEffect(id, org, fwd);
}

void FX_PlayEntityEffectID( int id, const vec3_t org,
						const vec3_t axis[3], const int boltInfo, const int entNum )
{
	theFxScheduler.PlayEffect(id, org, axis, boltInfo, entNum);
}

void FX_PlayBoltedEffectID( int id, sharedBoltInterface_t *fxObj )
{
	CFxBoltInterface obj;

	obj.SetEntNum(fxObj->entNum);
	obj.SetBoltNum(fxObj->boltNum);
	obj.SetForward(fxObj->angles);
	obj.SetG2Handle(fxObj->ghoul2);
	obj.SetModelNum(fxObj->modelNum);
	obj.SetOrigin(fxObj->origin);
	obj.SetScale(fxObj->scale);

	obj.Validate();

	theFxScheduler.PlayEffect(id, &obj);
}

void FX_AddScheduledEffects( void )
{
	theFxScheduler.AddScheduledEffects();
	tc_vis_render();
}

int FX_InitSystem( void )
{
	return FX_Init();
}

qboolean FX_FreeSystem( void )
{
	return (qboolean)FX_Free();
}

void FX_AdjustTime_Pos( int time, const vec3_t refdef_vieworg, const vec3_t refdef_viewaxis[3] )
{
	theFxHelper.AdjustTime_Pos( time, refdef_vieworg, refdef_viewaxis );
}

