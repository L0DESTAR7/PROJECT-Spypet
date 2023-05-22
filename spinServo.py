from gpiozero import Servo
from time import sleep

from gpiozero.pins.pigpio import PiGPIOFactory

factory = PiGPIOFactory()
def spinServo(servo, position):
    if position == "close":
        print("Closing the gate.")
        servo.max()
        sleep(1)
    elif position == "open":
        print("Opening the gate.")
        servo.min()
        sleep(1)
    else:
        raise ValueError("Invalid position value provided")