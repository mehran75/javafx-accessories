import com.mpatric.mp3agic.Mp3File;
import constant.Constants;
import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncodingAttributes;

import javax.sound.sampled.*;
import java.io.File;

public class VoiceRecorder {

    private final float sampleRate = 44100.0F;
    private Thread stopper;
    private TargetDataLine line;
    private String fileName;
    private boolean isRecording = false;
    private String fileTargetExtinction = ".mp3";

    public static int getMp3Duration(File file) throws Exception {

        Mp3File mp3file = new Mp3File(file);


        return Math.toIntExact(mp3file.getLengthInSeconds());
    }

    public void record() throws Exception {

        FileChecker.buildMainFolders();

        AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                44100
                , 16, 2, 4, 44100, false);

        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format); // format is an AudioFormat object
        if (!AudioSystem.isLineSupported(info)) {
            // Handle the error ...
            throw new Exception("Device can't record voice");

        }
// Obtain and open the line.
        try {
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            // Assume that the TargetDataLine, line, has already

// Begin audio capture.
            fileName = String.valueOf(System.currentTimeMillis());
            line.start();

            stopper = new Thread(() -> {

                try {

                    File file = new File(Constants.VOICE_FOLDER_PATH + File.separator + fileName + ".wav");
                    AudioInputStream stream = new AudioInputStream(line);

                    AudioSystem.write(stream, AudioFileFormat.Type.WAVE, file);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            stopper.start();
            isRecording = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String stopRecording() throws Exception {
        isRecording = false;
        stopper.interrupt();

        line.stop();
        line.close();

        File source = new File(Constants.VOICE_FOLDER_PATH + File.separator + fileName + ".wav");
        File target = new File(Constants.VOICE_FOLDER_PATH + File.separator + fileName + fileTargetExtinction);

        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("libmp3lame");
        audio.setBitRate(128000);
        audio.setChannels(2);
        audio.setSamplingRate(44100);
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("mp3");
        attrs.setAudioAttributes(audio);
        Encoder encoder = new Encoder();
        encoder.encode(source, target, attrs);
        source.delete();


        return fileName + fileTargetExtinction;

    }

    public boolean isRecording() {
        return isRecording;
    }
}
