import socket
import webbrowser
import os
from sys import platform

host = ""
port = 27015
#opening password.txt to access password
with open("password.txt","r") as file:
	for line in lines:
		password = line

s = socket.socket()
s.bind((host, port))
s.listen(4)

while True :
	phone, addr = s.accept()
	msg = phone.recv(1024)
	msg = str(msg[2:], 'utf-8')
	recvPass = msg.split(' ', 1)[0]

	if recvPass == password :
		url = msg.split(' ', 1)[1]
		if url != "" :
			webbrowser.open(url)
		if platform == 'linux' or platform == 'linux2' :
			dm = os.environ.get("DESKTOP_SESSION")
			if 'gnome' in dm :
				os.system('gnome-screensaver-command -d')
			if 'kde' in dm :
				os.system('loginctl unlock-session')

s.close()
