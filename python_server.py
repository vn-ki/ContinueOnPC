import socket
import webbrowser
import os
from sys import platform

host = "10.250.34.133"
port = 27015

password = "abcd" #Ideally read from a file

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
