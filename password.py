#getting password input
password = input("Enter the password you would like to have :")
#writing password into password.txt
with open('password.txt','w') as file:
    file.write(password)