import pkg from 'node-cron';
const { cron } = pkg;
import { exec } from "child_process";

export const createSpinTask = (orderDate, periodicity, pythonScriptPath) => {
    const dateObj = new Date(orderDate);
    const cronJobDate = dateObj.toISOString().replace(/\..+/, '');

    let cronSchedule;
    if (periodicity === "DAILY") {
        cronSchedule = `${cronJobDate.split('T')[1].substring(3, 5)} ${cronJobDate.split('T')[1].substring(0, 2)} * * *`;
    } else if (periodicity === "WEEKLY") {
        cronSchedule = `0 ${cronJobDate.split('T')[1].substring(3, 5)} * * ${cronJobDate.split('T')[0].substring(8, 10)} *`;
    } else if (periodicity === "MONTHLY") {
        cronSchedule = `0 ${cronJobDate.split('T')[1].substring(3, 5)} ${cronJobDate.split('T')[0].substring(5, 7)} * * *`;
    } else if (periodicity === "YEARLY") {
        cronSchedule = `0 ${cronJobDate.split('T')[1].substring(3, 5)} ${cronJobDate.split('T')[0].substring(8, 10)} ${cronJobDate.split('T')[0].substring(5, 7)} * *`;
    } else if (periodicity === "NONE") {
        cronSchedule = `0 ${cronJobDate.split('T')[1].substring(3, 5)} ${cronJobDate.split('T')[0].substring(8, 10)} ${cronJobDate.split('T')[0].substring(5, 7)} * * *`;
    } else {
        throw new Error('Invalid periodicity.');
    }

    console.log(cron);
    console.log(cronSchedule);
    const cronJob = cron.schedule(cronSchedule, () => {
        console.log('Cron job executed at:', new Date());

        exec(`/usr/bin/python3 ${pythonScriptPath}`, (error, stdout, stderr) => {
            if (error) {
                console.error(`Failed to run Python script: ${error.message}`);
                return;
            }
            console.log(`Python script output: ${stdout}`);
        });
    }, {
        scheduled: true,
        timezone: 'GMT',
    });

    cronJob.start();

    /*const cronJob = spawn('crontab', ['-l']);
    cronJob.on('exit', (code) => {
        if (code === 0){
            cronJob.stdout.on('data', (data) => {
                const cronJobsList = data.toString().split('\n');
                const newCronJob = `${cronSchedule} /usr/bin/python3 ${pythonScriptPath}`;
                if (!cronJobsList.includes(newCronJob)) {
                    cronJobsList.push(newCronJob);
                    const updatedCronJob = cronJobsList.join('\n');
                    const updateCronJob = spawn('sudo crontab', ['-e'], {stdio: 'pipe'});
                    updateCronJob.stdin.write(updatedCronJob);
                    updateCronJob.stdin.end();

                    updateCronJob.on('close', (code) => {
                        console.log(code);
                    })
                    updateCronJob.on('error', (error) => {
                        console.log(error);
                    })
                    updateCronJob.on('exit', (codez) => {
                        if (codez === 0) {
                            console.log('Cron job added successfully');
                        } else {
                            console.error('Failed to add cron job haha', codez);
                        }
                    });
                } else {
                    console.log('Cron job already exists');
                }
            });
        } else if (code === 1) {
            const newCronJob = `${cronSchedule} /usr/bin/python3 ${pythonScriptPath}`;
            const updateCronJob = spawn('crontab', ['-l', '-'], {stdio: 'pipe'});
            updateCronJob.stdin.write(newCronJob);
            updateCronJob.stdin.end();
            updateCronJob.on('exit', (code) => {
                if (code === 0){
                    console.log('Cron Job added successfully');
                } else {
                    console.log('Failed to add cron job héhé');
                }
            });
        } else {
            console.error(`Failed to get cron jobs: Exit code ${code}`);
        }
    });

    cronJob.stderr.on('data', (data) => {
        console.error(`Failed to get cron jobs: ${data.toString()}`);
    });
    cronJob.on('error', (err) => {
        console.error(`Failed to get cron job: ${err.message}`);
    })*/
};

