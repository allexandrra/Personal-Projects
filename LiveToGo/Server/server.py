import socket
from _thread import *
import keyboard
import re

HOST = '192.168.1.4'
PORT = 8000
maxCon = 999
eventList = {}

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind((HOST, PORT))
s.listen(maxCon)

def on_new_client(client) :
    while True:
        clientType = client.recv(1024).decode()
        if clientType == "source":
            link = client.recv(1024).decode()
            p = re.compile('\/([a-z]+)\/')
            eventName = p.findall(link)[0]
            if not eventName:
                break
            else:
                if eventName in eventList.keys():
                    eventList[eventName].append(link)
                else:
                    eventList[eventName] = []
                    eventList[eventName].append(link)
            print(str(eventList))
            break
        if clientType == "destination":
            eventName = client.recv(1024).decode()
            while True:
                source = client.recv(1024).decode()
                if source == '1':
                    client.sendall(eventList[eventName][0].encode('utf-8'))
                if source == '2':
                    client.sendall(eventList[eventName][1].encode('utf-8'))
                if source == '3':
                    client.sendall(eventList[eventName][2].encode('utf-8'))
                if source == 'q':
                    bye = "quit"
                    client.sendall(bye.encode('utf-8'))
                    break
            break

    client.close()

while True:
    (client, addr) = s.accept()
    start_new_thread(on_new_client, (client, ))

s.close()