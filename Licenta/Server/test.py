import cv2
import numpy as np

cap = cv2.VideoCapture("rtmp://192.168.1.4:1935/live/myStream")

currentFrame = 0

while True:
    ret, frame = cap.read()

    if not frame:
        print("there is no frame")
        break

    cv2.imshow('frame', frame)

    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

    currentFrame += 1

cap.release()
cv2.destroyAllWindows()
