print("EXECUTING ORDER!")
from gpiozero import Servo
from time import sleep

from gpiozero.pins.pigpio import PiGPIOFactory

factory = PiGPIOFactory()

servo = Servo(12, min_pulse_width=0.5/1000, max_pulse_width=2.5/1000,pin_factory=factory)

for i in range(10):
    if(i % 2 == 0):
        servo.min()
        sleep(2)
    else:
        servo.max()
        sleep(2)

print("Done! Going back to middle")
servo.mid()
sleep(2)
servo.value = None
