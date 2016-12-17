
package org.firstinspires.ftc.robotcontroller.internal;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="BotHardware", group="Opmode")

public class BotHardware extends LinearOpMode
{
    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();

    DcMotor wfr = null;
    DcMotor wbr = null;
    DcMotor wfl = null;
    DcMotor wbl = null;

    Servo beaconLeftServo, beaconRightServo;

//    ModernRoboticsI2cGyro gyro;
//    UltrasonicSensor sonar;
//    ColorSensor ground;
    ColorSensor beaconLeft;
    ColorSensor beaconRight;
    public int good = 0;

    int state = 0;
    private double startTime = 0;


    @Override
    public void runOpMode() throws InterruptedException {
        try {
            wfr = hardwareMap.dcMotor.get("wfr");
            wbr = hardwareMap.dcMotor.get("wbr");
            wfl = hardwareMap.dcMotor.get("wfl");
            wbl = hardwareMap.dcMotor.get("wbl");

            wbl.setDirection(DcMotor.Direction.REVERSE);
            wfl.setDirection(DcMotor.Direction.REVERSE);

            wfr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
            wbr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
            wfl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
            wbl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODERS);
        } catch (Exception e) {
            telemetry.addData("[ERROR]:", "motor error");
        }
        try {
            beaconLeftServo = hardwareMap.servo.get("beaconLeftServo");
            beaconRightServo = hardwareMap.servo.get("beaconRightServo");
        }
        catch(Exception e)
        {
            telemetry.addData("[ERROR]:", "servo error");
        }
        //GYRO SENSOR
//        try
//        {
//            gyro = (ModernRoboticsI2cGyro)hardwareMap.gyroSensor.get("gyro");
//            gyro.calibrate();
//        }
//        catch (Exception e)
//        {
//            telemetry.addData("[ERROR]:", "gyro sensor setup");
//        }
//        //GROUND COLOR SENSOR
//        try
//        {
//            ground = hardwareMap.colorSensor.get("ground");
//        }
//        catch (Exception e)
//        {
//            telemetry.addData("[ERROR]:", "color sensor setup");
//        }
        //BEACON LEFT COLOR SENSOR
        try
        {
            beaconLeft = hardwareMap.colorSensor.get("beaconLeft");
        }
        catch (Exception e)
        {
            telemetry.addData("[Error]:", "left beacon color sensor setup");
        }
        //BEACON RIGHT COLOR SENSOR
        try
        {
            beaconRight = hardwareMap.colorSensor.get("beaconRight");
        }
        catch (Exception e)
        {
            telemetry.addData("[Error]:", "right beacon color sensor setup");
        }
//        //ULTRASONIC DISTANCE SENSOR
//        try
//        {
//            sonar = hardwareMap.ultrasonicSensor.get("sonar");
//        }
//        catch (Exception e)
//        {
//            telemetry.addData("[ERROR]:", "sonar sensor setup");
//        }
//        while (gyro.isCalibrating())
//        {
//            Thread.sleep(50);
//        }
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        startTime = getRuntime();
    }


    //custom methods
    public void setPower(float left, float right)
    {
        wfl.setPower(left);
        wbl.setPower(left);
        wfr.setPower(right);
        wbr.setPower(right);
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
//        telemetry.addData("Gyro", gyro.getIntegratedZValue());
        telemetry.update();
    }

//    float errorRoom = 2;
//    public void driveGyro(float power)
//    {
//        if(gyro.getIntegratedZValue() > errorRoom) {
//            setPower(-power, power);
//        } else if(gyro.getIntegratedZValue() < -errorRoom) {
//            setPower(power, -power);
//        } else {
//            setPower(power);
//        }
//    }
//
//    float white = 100;
//    public boolean isOnLine()
//    {
//        return ground.red() > white && ground.blue() > white && ground.green() > white;
//    }

    float scaleInput(float input)
    {
        input = Range.clip(input, -1, 1);
        if (input > 0)
            return (float)Math.pow(input, 4);
        return -(float)Math.pow(input, 4);
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