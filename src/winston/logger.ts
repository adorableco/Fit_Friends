import moment from 'moment-timezone';

const wiston = require('winston');
const winstonDaily = require('winston-daily-rotate-file');
const { combine, timestamp, label, printf } = wiston.format;

const logDir = `logs`;

moment.tz.setDefault("Asia/Seoul");
const timeStamp = () => moment().format('YYYY-MM-DD HH:mm:ss');


const logFormat = printf(({ level, message, label }: { level: string; message: string; label: string }) => {
    return `${timeStamp()} [${label}] ${level}: ${message}`;
});

const logger = wiston.createLogger({
    level: 'info',
    format:combine(label({ label: 'AI_REVIEW' }), timestamp(), logFormat),
    transports: [
        new winstonDaily({
            level : "info",
            datePattern: 'YYYY-MM-DD-HH',
            dirname: logDir,
            filename: `%DATE%.log`,
            maxSize: "20m",
            maxFiles: "30d",
        }),
        new winstonDaily({
            level: "error",
            datePattern: 'YYYY-MM-DD',
            dirname: logDir + '/error',
            filename: `%DATE%.error.log`,
            maxSize: "20m",
            maxFiles: "30d",
        })
    ]
});

module.exports = logger;

