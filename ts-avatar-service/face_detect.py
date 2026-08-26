import cv2
import mediapipe as mp
import base64
import numpy as np

path_save = "./images/"

# MediaPipe Face Detection
mp_face_detection = mp.solutions.face_detection
face_detector = mp_face_detection.FaceDetection(model_selection=1, min_detection_confidence=0.5)

def check(img):
    # MediaPipe face detection
    results = face_detector.process(cv2.cvtColor(img, cv2.COLOR_BGR2RGB))
    
    faces = []
    if results.detections:
        h, w, c = img.shape
        for detection in results.detections:
            bbox = detection.location_data.bounding_box
            x_min = int(bbox.xmin * w)
            y_min = int(bbox.ymin * h)
            x_max = int((bbox.xmin + bbox.width) * w)
            y_max = int((bbox.ymin + bbox.height) * h)
            faces.append((x_min, y_min, x_max - x_min, y_max - y_min))
    
    print("人脸数：", len(faces), "\n")

    if len(faces) < 1:
        return {"msg":"no human face found"}

    # 记录人脸矩阵大小
    height_max = 0
    width_sum = 0

    # 计算要生成的图像 img_blank 大小
    for k, (x, y, w, h) in enumerate(faces):
        # 计算矩形框大小
        height = h
        width = w

        # 根据人脸大小生成空的图像
        img_blank = np.zeros((height, width, 3), np.uint8)

        for i in range(height):
            for j in range(width):
                img_blank[i][j] = img[y + i][x + j]

        print("Save to:", path_save + "img_face_" + str(k + 1) + ".jpg")
        cv2.imwrite(path_save + "img_face_" + str(k + 1) + ".jpg", img_blank)

        base64_str = cv2.imencode('.jpg',img_blank)[1].tobytes()
        base64_str = base64.b64encode(base64_str)
        return base64_str

