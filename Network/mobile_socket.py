import socket
import time
import server_socket

if __name__ == '__main__':
    host = ''
    port = 2022

    print("Server Connecting")
    client_socket, addr = server_socket.accept()
    print('Connectd by', addr)
    while True:
        data = client_socket.recv()

        if not data:
            break

        if data == "":
            start_position = data
        #shortest path algorithm
        
        #client_socket.sendall(data)




