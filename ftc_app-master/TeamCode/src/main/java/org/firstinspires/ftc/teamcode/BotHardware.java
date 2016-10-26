package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Driver TeleOp", group="Opmode")
public class BotHardware extends LinearOpMode {
    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();

    DcMotor MotorLeftFront = null;
    DcMotor MotorLeftBack = null;
    DcMotor MotorRightFront = null;
    DcMotor MotorRightBack = null;
    ModernRoboticsI2cGyro gyro;

    int state = 0;

    private double startTime = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        try {
            MotorLeftFront = hardwareMap.dcMotor.get("left_front_drive");
            MotorLeftBack = hardwareMap.dcMotor.get("right__front_drive");
            MotorRightFront = hardwareMap.dcMotor.get("left_back_drive");
            MotorRightBack = hardwareMap.dcMotor.get("right_back_drive");
        } catch (Exception e) {
            telemetry.addData("[ERROR]:", "motor error");
        }

        try {
            gyro = (ModernRoboticsI2cGyro)hardwareMap.gyroSensor.get("gyro");
            gyro.calibrate();
        } catch (Exception e) {
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
    public void setPower(float left, float right){
        MotorLeftFront.setPower(-left);
        MotorLeftBack.setPower(-left);
        MotorRightFront.setPower(right);
        MotorRightBack.setPower(right);
    }

    public void setPower(float power){
        setPower(power, power);
    }

    public double getTime(){
        return getRuntime() - startTime;
    }

    public void updateTelemetry(){
        telemetry.addData("Time", getTime());
        telemetry.addData("State", state);
        telemetry.update();
    }
}
