
#ifndef __LUA_LPACK_H_
#define __LUA_LPACK_H_

extern "C" {
#include "lua.h"
#include "lualib.h"
#include "lauxlib.h"
}

int luaopen_pack(lua_State *L);

#endif
