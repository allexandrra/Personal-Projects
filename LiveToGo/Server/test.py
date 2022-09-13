import cv2
import numpy as np

cap1 = cv2.VideoCapture("rtmp://192.168.1.4:1935/live/myStream")
cap2 = cv2.VideoCapture("rtmp://192.168.1.4:1935/live/test")

currentFrame = 0

while True:
    ret1, frame1 = cap1.read()
    ret2, frame2 = cap2.read()

    cv2.imshow('frame1', frame1)
    cv2.imshow('frame2', frame2)

    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

    currentFrame += 1

cap1.release()
cap2.release()
cv2.destroyAllWindows()