import os

host = os.getenv("AVATAR_MYSQL_HOST", "ts-avatar-mysql")
port = os.getenv("AVATAR_MYSQL_PORT", 3306)
user = os.getenv("AVATAR_MYSQL_USER", "root")
password = os.getenv("AVATAR_MYSQL_PASSWORD", "Abcd1234#")
db = os.getenv("AVATAR_MYSQL_DATABASE", "ts-avatar-mysql")
