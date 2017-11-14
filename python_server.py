import socket
import webbrowser

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
		webbrowser.open(url)

s.close()
