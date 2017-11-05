import socket
import webbrowser

host = "0.0.0.0"
port = 27015

s = socket.socket()
s.bind((host, port))
s.listen(4)

while True :
	phone, addr = s.accept()
	msg = phone.recv(1024)
	url = str(msg[2:], 'utf-8')
	webbrowser.open(url)

s.close()
