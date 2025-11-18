from flask import Flask, request, jsonify
import numpy as np
import urllib
import cv2
import os
import json
import base64
import traceback
import jwt
from datetime import datetime, timezone

from face_detect import check

app = Flask(__name__)

# TODO:
# ~~1. 获取图片~~
#  ~2. 检测图片是否ok
#  ~3. 人脸检测&切割
#  ~4. 返回base64格式的图片
# 5. 前端传文件
# 6. Dockerfile部署

receive_path = r"./received/"

# Secret key should be kept in environment variable in production
JWT_SECRET = os.environ.get("JWT_SECRET", "secret")
JWT_ALG = os.environ.get("JWT_ALG", "HS256")


def decode_jwt_from_header(req):
    """Extract and validate JWT from Authorization header. Returns claims dict or None."""
    auth = req.headers.get("Authorization")
    if not auth or not auth.startswith("Bearer "):
        return None
    token = auth[7:]
    try:
        claims = jwt.decode(token, JWT_SECRET, algorithms=[JWT_ALG])
        # check expiration
        exp = claims.get("exp")
        if exp and datetime.fromtimestamp(exp, tz=timezone.utc) < datetime.now(tz=timezone.utc):
            return None
        return claims
    except jwt.PyJWTError:
        return None

def get_user_info(req):
    claims = decode_jwt_from_header(req)
    if claims is None:
        return None
    return claims.get("id")

@app.route('/api/v1/avatar', methods=["POST"])
def hello():
    # jwt auth
    user_id = get_user_info(request)
    if user_id is None:
        return jsonify({"msg": "Invalid or missing token"}), 401

    # receive file
    data = request.get_data().decode('utf-8')
    data = json.loads(data)
    image_b64 = data.get("img")
    if image_b64 is None or len(image_b64) < 1:
        return jsonify({"msg": "need img in request body"}), 400

    try:
        image_decode = base64.b64decode(image_b64)
        nparr = np.fromstring(image_decode, np.uint8)
        image = cv2.imdecode(nparr, cv2.IMREAD_COLOR)
        result = check(image)
    except Exception as e:
        return jsonify({"msg": "exception:" + str(traceback.format_exc())}), 500

    if type(result) == dict and result.get("msg") is not None:
        return jsonify(result), 400

    # attach user info if needed
    return result, 200


if __name__ == '__main__':
    app.run(host="0.0.0.0", port=17001, debug=True)
