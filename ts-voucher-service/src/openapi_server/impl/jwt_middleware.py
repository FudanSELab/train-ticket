from __future__ import annotations

import os
from datetime import datetime, timezone
from typing import Callable, Awaitable

import jwt
from fastapi import FastAPI, Request
from fastapi.responses import JSONResponse

JWT_SECRET = os.environ.get("JWT_SECRET", "secret")
JWT_ALGORITHM = os.environ.get("JWT_ALGORITHM", "HS256")
PROTECTED_PATH_PREFIX = "/api/"


async def jwt_authentication_middleware(
    request: Request,
    call_next: Callable[[Request], Awaitable],
):
    """Validate JWT on protected API routes and expose claims on request state."""
    if not request.url.path.startswith(PROTECTED_PATH_PREFIX):
        return await call_next(request)

    auth_header = request.headers.get("Authorization")
    if not auth_header or not auth_header.startswith("Bearer "):
        return JSONResponse({"msg": "Invalid or missing token"}, status_code=401)

    token = auth_header[7:]
    try:
        claims = jwt.decode(token, JWT_SECRET, algorithms=[JWT_ALGORITHM])
    except jwt.ExpiredSignatureError:
        return JSONResponse({"msg": "Token expired"}, status_code=401)
    except jwt.PyJWTError:
        return JSONResponse({"msg": "Invalid token"}, status_code=401)

    exp = claims.get("exp")
    if exp and datetime.fromtimestamp(exp, tz=timezone.utc) < datetime.now(timezone.utc):
        return JSONResponse({"msg": "Token expired"}, status_code=401)

    request.state.user = claims
    return await call_next(request)


def setup_jwt_middleware(app: FastAPI) -> None:
    """Attach the JWT authentication middleware to the FastAPI app."""
    app.middleware("http")(jwt_authentication_middleware)

