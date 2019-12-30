import socket
import threading

PORT = 10001


def listen_socket(sck):
    while True:
        msg = sck.recv(1024)
        if msg.decode() == 'exit':
            break
        print(msg.decode())


def send_socket(sck):
    while True:
        msg = input()
        sck.send(msg.encode('utf-8'))
        if msg == 'exit':
            break


if __name__ == '__main__':

    # To send the message on server
    # Information about current location,
    send_sck = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    send_sck.connect(('141.223.162.232', PORT))
    camera = Camera.Camera(send_sck)
    send_thread = threading.Thread(target=send_socket, args=(send_sck, ))
    get_thread = threading.Thread(target=listen_socket, args=(send_sck, ))
    camera_thread = threading.Thread(target=camera.run, args=())
    camera_thread.start()

    send_thread.start()
    get_thread.start()

    # Wait until both threads are terminated. Then close.
    send_thread.join()
    get_thread.join()
    camera_thread.join()
