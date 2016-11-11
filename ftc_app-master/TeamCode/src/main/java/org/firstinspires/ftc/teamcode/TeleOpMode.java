package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

public class TeleOpMode {

    @TeleOp(name="Driver TeleOp", group="Opmode")
    public class TemplateOpMode_Linear extends BotHardware {
        @Override
        public void runOpMode() throws InterruptedException {
            super.runOpMode();

            while (opModeIsActive()) {
                updateTelemetry();
                setPower(gamepad1.left_stick_x, gamepad1.right_stick_y);
            }
        }
    }
}
