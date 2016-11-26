
package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Bot Hardware", group="Opmode")
public class BotHardware extends LinearOpMode
{
    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();

    DcMotor MotorLeftFront = null;















    DcMotor MotorLeftBack = null;
    DcMotor MotorRightFront = null;
    DcMotor MotorRightBack = null;
    ModernRoboticsI2cGyro gyro;
    UltrasonicSensor sonar;
    ColorSensor ground;
    public int good = 0;

    int state = 0;
    private double startTime = 0;


    @Override
    public void runOpMode() throws InterruptedException
    {
        try
        {
            MotorLeftFront = hardwareMap.dcMotor.get("left_front_drive");
            MotorLeftBack = hardwareMap.dcMotor.get("right__front_drive");
            MotorRightFront = hardwareMap.dcMotor.get("left_back_drive");
            MotorRightBack = hardwareMap.dcMotor.get("right_back_drive");
        }
        catch (Exception e)
        {
            telemetry.addData("[ERROR]:", "motor error");
        }
        try
        {
            gyro = (ModernRoboticsI2cGyro)hardwareMap.gyroSensor.get("gyro");
            gyro.calibrate();
        }
        catch (Exception e)
        {
            telemetry.addData("[ERROR]:", "gyro sensor setup");
        }
        try
        {
            ground = hardwareMap.colorSensor.get("ground");
        }
        catch (Exception e)
        {
            telemetry.addData("[ERROR]:", "color sensor setup");
        }
        try
        {
            sonar = hardwareMap.ultrasonicSensor.get("sonar");
        }
        catch (Exception e)
        {
            telemetry.addData("[ERROR]:", "sonar sensor setup");
        }
        while (gyro.isCalibrating())
        {
            Thread.sleep(50);
        }

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        startTime = getRuntime();
    }


    //custom methods
    public void setPower(float left, float right)
    {
        MotorLeftFront.setPower(-left);
        MotorLeftBack.setPower(-left);
        MotorRightFront.setPower(right);
        MotorRightBack.setPower(right);
    }

    public void setPower(float power)
    {
        setPower(power, power);
    }

    public double getTime()
    {
        return getRuntime() - startTime;
    }

    public void setTime()
    {
        startTime = getRuntime();
    }

    public void updateTelemetry()
    {
        telemetry.addData("Time", getTime());
        telemetry.addData("State", state);
        telemetry.addData("Gyro", gyro.getIntegratedZValue());
        telemetry.update();
    }

    float errorRoom = 2;
    public void driveGyro(float power)
    {
        if(gyro.getIntegratedZValue() > errorRoom) {
            setPower(-power, power);
        } else if(gyro.getIntegratedZValue() < -errorRoom) {
            setPower(power, -power);
        } else {
            setPower(power);
        }
    }

    float white = 100;
    public boolean isOnLine(){
        return ground.red() > white && ground.blue() > white && ground.green() > white;
    }
}








/*package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Driver TeleOp", group="Opmode")
public class BotHardware extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    DcMotor MotorLeftFront = null;
    DcMotor MotorLeftBack = null;
    DcMotor MotorRightFront = null;
    DcMotor MotorRightBack = null;
    ModernRoboticsI2cGyro gyro;

    int state = 0;

    final int degreeError = 2;
    final int whiteLevel = 100;

    Servo thrower, beaconServo;
    ColorSensor groundLeft, beacon;
    ModernRoboticsI2cGyro gyro;
    UltrasonicSensor sonar;
    private double startTime = getRuntime();


    @Override
    public void runOpMode() throws InterruptedException
    {
        try
        {
            MotorLeftFront = hardwareMap.dcMotor.get("left_front_drive");
            MotorLeftBack = hardwareMap.dcMotor.get("right__front_drive");
            MotorRightFront = hardwareMap.dcMotor.get("left_back_drive");
            MotorRightBack = hardwareMap.dcMotor.get("right_back_drive");
        } catch (Exception e)
        {
            telemetry.addData("[ERROR]:", "motor error");
        }

        try
        {
            gyro = (ModernRoboticsI2cGyro)hardwareMap.gyroSensor.get("gyro");
            gyro.calibrate();
        } catch (Exception e)
        {
            telemetry.addData("[ERROR]:", "gyro sensor setup");
        }

        while (gyro.isCalibrating())
        {
            Thread.sleep(50);
        }

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        startTime = getRuntime();
    }


    //custom methods
    public void setPower(float left, float right)
    {
        MotorLeftFront.setPower(-left);
        MotorLeftBack.setPower(-left);
        MotorRightFront.setPower(right);
        MotorRightBack.setPower(right);
    }


    //resets the time in the robot to the time it's been running
    public void setTime()
    {
        startTime = this.getRuntime();
    }


    //activates gyro to a set power, drives
    public void driveGyro(float power)
    {
        if (gyro.getIntegratedZValue() > degreeError)
            setPower(-power, power);
        else if (gyro.getIntegratedZValue() < -degreeError)
            setPower(power, -power);
        else
            setPower(power, power);

    }


    //sees with the RGB sensor
    public boolean isLeftOnLine()
    {
        return groundLeft.red() > whiteLevel && groundLeft.blue() > whiteLevel && groundLeft.green() > whiteLevel;
    }

    public void setPower(float power)
    {
        setPower(power, power);
    }

    public double getTime()
    {
        return getRuntime() - startTime;
    }

    public void updateTelemetry()
    {
        telemetry.addData("Time", getTime());
        telemetry.addData("State", state);
        telemetry.update();
    }
}
*/