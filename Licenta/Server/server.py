import socket
import cv2
import numpy as np

HOST = '192.168.1.4'
PORT = 5000

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
print('socket created')

s.bind((HOST, PORT))
print('socket bind completed')

# a Q of 5
s.listen(5)
print('socket now listening')

while True:
    try:
        conn, addr = s.accept()
        print(f'client connected with {addr}')

        dim = conn.recv(1024)
        print(dim)

    except KeyboardInterrupt:
        s.close()