package org.firstinspires.ftc.robotcontroller.internal;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="AutoRed", group="Autonomous")

public class AutoRed extends BotHardware
{
    @Override
    public void runOpMode() throws InterruptedException
    {
        super.runOpMode();

        short state = 0;
        short close, far, good;

        close = far = good = 0;

        while (gyro.isCalibrating())
        {
            Thread.sleep(50);
        }

        waitForStart();

        gyro.resetZAxisIntegrator();

        setTime();

        while (opModeIsActive())
        {
            switch (state)
            {
                case 0://start drive
                    setPower(0.25f);
                    state++;
                    break;
                case 1: //after 1 sec stop to turn left
                    if(getTime() > 1)
                    {
                        setPower(0);
                        state++;
                    }
                    break;
                case 2: //first turn 45 degrees left //CHANGE
                    setPower(0.35f, -0.35f);
                    if(Math.abs(gyro.getIntegratedZValue()) > 44)
                    {
                        setPower(0);
                        state++;
                    }
                    break;
                case 3: //reset gyro
                    gyro.resetZAxisIntegrator();
                    state++;
                    break;
                case 4: //drive until white line
                case 12:
                    driveGyro(0.3f);
                    if(getTime() > 10)
                    {
                        setPower(0);
                        state = 100;
                        break;
                    }
                    if(isOnLine())
                    {
                        state ++;
                        setTime();
                    }
                    break;
                case 5: //turn left
                    if(getTime() > 0.2)
                    {
                        gyro.resetZAxisIntegrator();
                        setPower(0.35f, -0.35f);
                        state++;
                    }
                    break;
                case 6: //getting into proper position on white line
                case 14:
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
                case 7: //drive forward until good distance for measuring color of beacon
                case 15:
                    if(sonar.getUltrasonicLevel() < 21)
                    {
                        far = good = 0;
                        close++;
                    }
                    else if (sonar.getUltrasonicLevel() > 24)
                    {
                        close = good = 0;
                        far++;
                    }
                    else
                    {
                        close = far = 0;
                        good++;
                    }

                    if (close == 2)
                    {
                        setPower(-0.1f);
                        close = 0;
                    }
                    else if (far == 2)
                    {
                        setPower(0.1f);
                        far = 0;
                    }
                    else if (good == 2)
                    {
                        setPower(0);
                        state++;
                        close = far = good = 0;
                    }
                    break;
                case 8: //beacon pusher
                case 16:
                    try
                    {
                        Thread.sleep(2500);
                    }
                    catch (InterruptedException e)
                    {
                        telemetry.addData("ERROR", e.getStackTrace()[0]);
                    }
                    if(beaconLeft.red() < 5 && beaconLeft.blue() < 5
                            && beaconRight.red() < 5 && beaconRight.blue() < 5)
                    {
                        beaconRightServo.setPosition(0.1);
                        beaconLeftServo.setPosition(0.1);
                        telemetry.addData("Beacon", "FAILED");
                        state = 100;
                        break;
                    }
                    if(beaconRight.red() > beaconRight.blue() && beaconLeft.red() < beaconLeft.blue() )
                    {
                        telemetry.addData("RED color: ", beaconRight.red());
                        telemetry.addData("BLUE color: ", beaconRight.blue());
                        try {
                            Thread.sleep(2000);
                        }catch (InterruptedException e){
                            telemetry.addData("ERROR", e.getStackTrace()[0]);
                        }
                        beaconRightServo.setPosition(1);
                        beaconLeftServo.setPosition(0);
                    }
                    else
                    {
                        telemetry.addData("BLUE color: ", beaconRight.blue());
                        telemetry.addData("RED color: ", beaconRight.red());
                        try
                        {
                            Thread.sleep(2000);
                        }
                        catch (InterruptedException e)
                        {
                            telemetry.addData("ERROR", e.getStackTrace()[0]);
                        }
                        beaconRightServo.setPosition(0);
                        beaconLeftServo.setPosition(1);
                    }
                    try
                    {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e)
                    {
                        telemetry.addData("ERROR", e.getStackTrace()[0]);
                    }
                    state++;
                    setPower(0.35f);
                    break;
                case 9: //drive forward until push beacon
                    try
                    {
                        Thread.sleep(1250);
                    }
                    catch (InterruptedException e)
                    {
                        telemetry.addData("ERROR", e.getStackTrace()[0]);
                    }
                    setPower(0);
                    state++;
                    break;
                case 10: //drive backwards a little
                case 17:
                    if (getTime() < 0.2)
                    {
                        setPower(-.35f);
                    }
                    else
                    {
                        setPower(0);
                        setTime();
                    }

                case 11: //rotate 90 degrees right
                    gyro.resetZAxisIntegrator();
                    setPower(0.35f, -0.35f);
                    if(Math.abs(gyro.getIntegratedZValue()) > 89)
                    {
                        setPower(0);
                        state++;
                    }
                    break;
                case 13: //rotate 90 degrees to face beacon again left
                    gyro.resetZAxisIntegrator();
                    setPower(0.35f, -0.35f);
                    if(Math.abs(gyro.getIntegratedZValue()) > 89)
                    {
                        setPower(0);
                        state++;
                    }
                    break;
                case 18: //fix angle to go backwards (turn right)
                    gyro.resetZAxisIntegrator();
                    setPower(0.35f, -0.35f);
                    if(Math.abs(gyro.getIntegratedZValue()) > 60)
                    {
                        setPower(0);
                        state++;
                    }
                case 19: //drive backwards to center
                    if (getTime() < 10)
                        setPower(-0.35f);
                    else
                    {
                        setPower(0);
                        setTime();
                    }
                default:
                    setPower(0);
                    break;
            }
        updateTelemetry();
        }
    }
}
