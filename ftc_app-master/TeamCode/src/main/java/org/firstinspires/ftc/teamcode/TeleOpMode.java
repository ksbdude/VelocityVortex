package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

    @TeleOp(name="TeleOpMode", group="Opmode")
    public class TeleOpMode extends BotHardware
    {
        @Override
        public void runOpMode() throws InterruptedException
        {
            telemetry.addData("", "1");
            super.runOpMode();

            telemetry.addData("", "2");
            while (opModeIsActive()) {
                telemetry.addData("", "3");
                setPower(gamepad1.left_stick_x, gamepad1.right_stick_y);
            }
        }
    }

