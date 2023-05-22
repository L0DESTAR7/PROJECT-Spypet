from gpiozero import Servo
import time
from gpiozero.pins.pigpio import PiGPIOFactory
from getOneWeight import getOneWeight
from spinServo import spinServo

factory = PiGPIOFactory()

def ejectWeight(weight_to_eject):
    if weight_to_eject > getOneWeight():
        print("ERROR : INSUFICIENT WEIGHT IN FOOD TANK")
    else:
        servo = Servo(12, min_pulse_width=0.5/1000, max_pulse_width=2.5/1000,pin_factory=factory)
        initial_weight = getOneWeight()
        spinServo(servo, "open")
        while True:
            time.sleep(0.1)
            print(getOneWeight())
            if getOneWeight() <= initial_weight - weight_to_eject : 
                spinServo(servo, "close")
                break


                


