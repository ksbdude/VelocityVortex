package org.firstinspires.ftc.robotcontroller.internal;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="TeleOpM", group="Opmode")
public class TeleOpM extends BotHardware {
    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();

        setPower(0f);

        // power values
        float wheelPower, turnPower;
        //float armPower;  //arm variable

        waitForStart();

        while (opModeIsActive()) {
            // ----------------
            // gamepad1:
            // ----------------

            wheelPower = gamepad1.left_stick_y;
            turnPower = -gamepad1.right_stick_x;

            if (gamepad1.right_bumper) {
                wheelPower = scaleInput(wheelPower) / 2;
                turnPower = scaleInput(turnPower) / 2;
            } else if (gamepad1.left_bumper) {
                wheelPower = scaleInput(wheelPower) / 4;
                turnPower = scaleInput(turnPower) / 4;
            } else {
                wheelPower = scaleInput(wheelPower);
                turnPower = scaleInput(turnPower);
            }

            // allows for tighter point turns
            if (Math.abs(turnPower) > 0.05f)
                setPower(turnPower, -turnPower);
            else
                setPower(-wheelPower);

            // --------------
            // gamepad2;
            // --------------

            if(gamepad2.x && beaconLeftServo.getPosition() != 1)
                beaconLeftServo.setPosition(1);
            else if(gamepad2.x && beaconLeftServo.getPosition() == 1)
                beaconLeftServo.setPosition(0);


            if(gamepad2.b && beaconRightServo.getPosition() != 1)
                beaconRightServo.setPosition(1);
            else if(gamepad2.b && beaconRightServo.getPosition() ==1)
                beaconRightServo.setPosition(0);

            /*previous version of button pusher controller

            if(gamepad2.x)
                beaconLeft.setPosition(1);
            else
                beaconLeft.setPosition(0);


            if(gamepad1.a)
                beaconRight.setPosition(1);
            else
                beaconRight.setPosition(0);

            waitOneFullHardwareCycle();
            */

        }
    }
}
