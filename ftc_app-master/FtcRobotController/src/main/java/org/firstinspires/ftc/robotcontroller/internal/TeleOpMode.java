package org.firstinspires.ftc.robotcontroller.internal;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="TeleOpMode", group="Opmode")
    public class TeleOpMode extends BotHardware
    {
        @Override
        public void runOpMode() throws InterruptedException
        {
            super.runOpMode();

            while (opModeIsActive())
            {
                setPower(gamepad1.left_stick_x, gamepad1.right_stick_y);
            }
        }
    }

