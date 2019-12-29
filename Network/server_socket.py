import socket
import threading

PORT = 10001


def listen_socket(sck):
    while True:
        msg = sck.recv(1024)
        if msg.decode() == 'exit':
            break
        reply = msg.decode()
        print(reply)


def send_socket(sck):
    while True:
        msg = input()
        sck.send(msg.encode('utf-8'))
        if msg == 'exit':
            break


if __name__ == '__main__':

    # To get the result from server.
    # Information about direction from server
    get_sck = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    get_sck.bind(('127.0.0.1', PORT))
    get_sck.listen(1)
    connection_sock, address = get_sck.accept()
    get_thread = threading.Thread(target=listen_socket, args=(connection_sock, ))
    send_thread = threading.Thread(target=send_socket, args=(connection_sock, ))
    get_thread.start()
    send_thread.start()

    # Wait until both threads are terminated. Then close.
    send_thread.join()
    get_thread.join()
    get_sck.close()
