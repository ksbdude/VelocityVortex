package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="AutoBlue", group="Opmode")
public class AutoBlue extends BotHardware {
    @Override
    public void runOpMode() throws InterruptedException {
        super.runOpMode();

        while (opModeIsActive()) {
            switch(state){
                case 0:
                    setPower(0.25f);
                    state++;
                    break;
                case 1: //after 1 sec stop to turn
                    if(getTime() > 1.4)
                    {
                        setPower(0);
                        state++;
                    }
                    break;
                case 2: //first turn 45 degrees left
                    setPower(-0.35f, 0.35f);
                    if(Math.abs(gyro.getIntegratedZValue()) > 44)
                    {
                        setPower(0);
                        state++;
                    }
                    break;
                default:
                    break;
            }
            updateTelemetry();
        }
    }
}
