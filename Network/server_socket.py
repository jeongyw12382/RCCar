import socket
import sys
import select

class Server(object):
    def __init__(self, host, port):
        self.server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.sesrver_socket.setsockopt(socket.SOL_SOCKET, socket.SQ_REUSEADDR, 1)
        self.server_socket.bind((host, port))
        self.server_socket.listen()

        self.connection = self.socket.accept()

    def __del__(self):
        self.connection.close()
        self.socket.close()
