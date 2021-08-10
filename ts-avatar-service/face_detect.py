#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

import cv2
import dlib
import base64
import numpy as np

path_save = "./images/"


detector = dlib.get_frontal_face_detector()

def check(img):
    # Dlib 检测器
    faces = detector(img, 1)
    print("人脸数：", len(faces), "\n")

    if len(faces) < 1:
        return {"msg":"no human face found"}


    # 记录人脸矩阵大小
    height_max = 0
    width_sum = 0

    # 计算要生成的图像 img_blank 大小
    for k, d in enumerate(faces):

        # 计算矩形大小
        # (x,y), (宽度width, 高度height)
        pos_start = tuple([d.left(), d.top()])
        pos_end = tuple([d.right(), d.bottom()])

        # 计算矩形框大小
        height = d.bottom() - d.top()
        width = d.right() - d.left()

        # 根据人脸大小生成空的图像
        img_blank = np.zeros((height, width, 3), np.uint8)

        for i in range(height):
            for j in range(width):
                img_blank[i][j] = img[d.top() + i][d.left() + j]

        print("Save to:", path_save + "img_face_" + str(k + 1) + ".jpg")
        cv2.imwrite(path_save + "img_face_" + str(k + 1) + ".jpg", img_blank)

        base64_str = cv2.imencode('.jpg',img_blank)[1].tostring()
        base64_str = base64.b64encode(base64_str)
        return base64_str

