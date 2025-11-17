from __future__ import annotations

from sqlalchemy import create_engine
from sqlalchemy.orm import declarative_base, sessionmaker, Session

import os

def _build_db_url() -> str:
    host = os.getenv("VOUCHER_MYSQL_HOST", "ts-voucher-mysql")
    port = os.getenv("VOUCHER_MYSQL_PORT", "3306")
    user = os.getenv("VOUCHER_MYSQL_USER", "root")
    password = os.getenv("VOUCHER_MYSQL_PASSWORD", "Abcd1234#")
    database = os.getenv("VOUCHER_MYSQL_DATABASE", "ts-voucher-mysql")
    return f"mysql+pymysql://{user}:{password}@{host}:{port}/{database}"


DATABASE_URL: str = _build_db_url()

engine = create_engine(
    DATABASE_URL,
    pool_pre_ping=True,
    future=True,
)

SessionLocal = sessionmaker(
    bind=engine,
    autocommit=False,
    autoflush=False,
    future=True,
)

Base = declarative_base()

def get_session() -> Session:
    """Provide a new SQLAlchemy session.

    Usage::
        with get_session() as session:
            ...
    """
    return SessionLocal()



