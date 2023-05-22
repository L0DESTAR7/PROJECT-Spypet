import subprocess
def getOneWeight():
   
    command = ["./loadcell_zaid/hx711/bin/getoneweight", "5", "6", "126", "424244"]
    process = subprocess.Popen(command, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    stdout, stderr = process.communicate()

    output_string = stdout.decode().strip()  # Convert bytes to string and remove leading/trailing whitespace

    # Print the captured string
    return float(output_string)

