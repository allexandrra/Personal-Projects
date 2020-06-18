import socket

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

port = 5000
s.connect((socket.gethostname(), port))
# 1024 is the buffer
# need to decide how big the chunks of data we want to receive
while True:
    msg = s.recv(1024)
    print(msg.decode("utf-8"))