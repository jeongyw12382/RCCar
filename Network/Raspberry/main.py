import server_connect
import rasp_serial
import Camera
import threading
import ultrasonic
import mic
import time

def run():
    HOST = '192.168.0.96'
    PORT = 5034
    #socket create for camera
    server_socket = server_connect.Connect(HOST, PORT)
    time.sleep(1)

    #create raspberrypi object
    serial = rasp_serial.Serial()    
    
    #if connected to server..start thread and send camera frame
    camera = Camera.Camera(server_socket.Get_Socket())
    camera_thread = threading.Thread(target=camera.run, args=())
    camera_thread.start()

    print('start')

    while True:
        data =  server_socket.Get_Data()
        if data == 'q' :
            break
        serial.steer(data)
    
if __name__ == "__main__" :
    run()
