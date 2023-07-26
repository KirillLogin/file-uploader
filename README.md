### What is it?

Homework for the I... S...

### What does this piece of code do?

- it generates a pair of random int numbers
- it calls https://loremflickr.com/width/height with the given generated numbers
- it downloads a file apd puts it into database
- also it puts into database summary about all persisted files

See application.yml for database settings.

Custom params:
- `parallelism` - how many files will be processed simultaneously
- `cron` - scheduler for the processing. Set to every 5 minutes by default
- `max-file-size-kb` - max allowed size for files downloaded from the external source

### How to launch
```docker-compose -f ./docker-compose.yml -p file-uploader up -d```

### How to check whether it's working or not

Look for "Starting the downloading" message in the console

### How to stop
```docker-compose -p file-uploader stop```