@echo off
echo Compiling the Banking System...
mkdir bin 2>nul
javac -d bin src\model\*.java src\view\*.java src\controller\*.java src\Main.java

if %errorlevel% neq 0 (
    echo Compilation failed.
    pause
    exit /b %errorlevel%
)

echo Compilation successful. Running the Banking System...
echo ====================================================
java -cp bin Main

echo.
pause
