package org.firstinspires.ftc.robotcontroller.internal;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="TeleOpM", group="Opmode")
public class TeleOpM extends BotHardware {
    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();

        waitForStart();

        while (opModeIsActive()) {
            // ----------------
            // gamepad1
            // ----------------

            if(gamepad1.right_stick_x != 0){
                setPower(-gamepad1.right_stick_x, gamepad1.right_stick_x);
            } else {
                setPower(-gamepad1.left_stick_y);
            }

            // --------------
            // gamepad2
            // --------------

            if(gamepad2.x) {
                beaconLeftServo.setPosition(0);
            }
            else
                beaconLeftServo.setPosition(1);


            if(gamepad2.b)
                beaconRightServo.setPosition(1);
            else
                beaconRightServo.setPosition(0);

        }
    }
}
