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
                case 1: //after 1 sec stop
                    if(getTime() > 1)
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
                case 3:
                    gyro.resetZAxisIntegrator();
                    state++;
                    break;
                case 4://drive until white
                case 11:
                    driveGyro(0.25f);
                    if(isWhite()) {
                        state++;
                        setTime();
                    }
                    break;
                case 5:
                    if(getTime() > 0.2) {
                        gyro.resetZAxisIntegrator();
                        setPower(-0.25f, 0.25f);
                        state++;
                    }
                    break;
                case 6:
                    if(Math.abs(gyro.getIntegratedZValue()) > 55)
                    {
                        setPower(-0.35f, 0.35f);
                    }
                    else if(Math.abs(gyro.getIntegratedZValue()) > 44)
                    {
                        setPower(0f);
                        gyro.resetZAxisIntegrator();
                        state++;
                    }
                    break;
                case 7://drive until correct distance
                case 13:
                    if(sonar.getUltrasonicLevel() < 21)
                    {
                        good = 0;
                        setPower(-0.1f);
                    }
                    else if (sonar.getUltrasonicLevel() > 24)
                    {
                        good = 0;
                        setPower(0.1f);
                    }
                    else
                    {
                        setPower(0);
                        good++;
                        if(good > 3){
                            state++;
                        }
                    }
                    break;
                case 8://press beacon
                case 14:

                case 9://backup

                case 10://turn right

                case 12://turn left

                    break;

                default:
                    break;
            }
            updateTelemetry();
        }
    }
}
