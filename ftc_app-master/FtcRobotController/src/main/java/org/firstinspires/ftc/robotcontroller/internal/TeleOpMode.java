package org.firstinspires.ftc.robotcontroller.internal;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

//TO TEST
@TeleOp(name="TeleOpMode", group="Opmode")
    public class TeleOpMode extends BotHardware
    {
        @Override
        public void runOpMode() throws InterruptedException
        {
            super.runOpMode();

            while (opModeIsActive())
            {
                //setPower(-gamepad1.left_stick_x, -gamepad1.right_stick_y);


                //possibly more comfortable controls, may need some adjustment for direction
                //similar to last years
                if(gamepad1.right_stick_x != 0){
                    setPower(-gamepad1.right_stick_x, gamepad1.right_stick_x);
                } else
                    setPower(gamepad1.left_stick_y);
            }
        }
    }

