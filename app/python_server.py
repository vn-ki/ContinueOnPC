import socket
import webbrowser
from time import sleep

host = "10.250.34.133"
port = 27015

s = socket.socket()
s.bind((host, port))
s.listen(4)

while True :
	phone, addr = s.accept()
	msg = phone.recv(1024)

	url = str(msg[2:], 'utf-8')
	print (msg)

	chrome_path = '/usr/bin/google-chrome %s'

	webbrowser.get(chrome_path).open(url)
s.close()
