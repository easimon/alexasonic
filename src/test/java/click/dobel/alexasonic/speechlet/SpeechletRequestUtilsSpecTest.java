package click.dobel.alexasonic.speechlet;

import click.dobel.alexasonic.exception.AlexaSonicException;
import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.*;
import com.amazon.ask.model.interfaces.audioplayer.AudioPlayerState;
import com.amazon.ask.model.interfaces.audioplayer.PlayerActivity;
import com.amazon.ask.model.interfaces.system.SystemState;
import com.greghaskins.spectrum.Spectrum;
import org.junit.runner.RunWith;

import java.util.Locale;
import java.util.Optional;

import static com.greghaskins.spectrum.Spectrum.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(Spectrum.class)
public class SpeechletRequestUtilsSpecTest {

    private static final String TEST_REQUEST_ID = "SomeRequestId";
    private static final String TEST_DEVICE_ID = "DeviceTestId-9876543210";
    private static final String TEST_USER_ID = "UserTestId-9876543210";
    private static final String SHORT_ID = "6543210";

    {

        describe(SpeechletRequestUtil.class.getSimpleName(), () -> {
            describe("#getDeviceId", () -> {
                it("returns the device Id when it is contained in the request", () -> {
                    final Device device = Device.builder().withDeviceId(TEST_DEVICE_ID).build();
                    final SystemState state = SystemState.builder().withDevice(device).build();
                    final Context context = Context.builder().withSystem(state).build();
                    final RequestEnvelope requestEnvelope = RequestEnvelope.builder()
                        .withContext(context)
                        .build();
                    assertThat(SpeechletRequestUtil.getDeviceId(requestEnvelope)).isEqualTo(TEST_DEVICE_ID);
                });
                it("throws an exeption when the request contains no device id", () -> {
                    final RequestEnvelope requestEnvelope = RequestEnvelope.builder().build();
                    assertThatExceptionOfType(AlexaSonicException.class)
                        .isThrownBy(() -> SpeechletRequestUtil.getDeviceId(requestEnvelope))
                        .withMessage(SpeechletRequestUtil.MESSAGE_NODEVICEID);
                });
            });

            describe("#getShortDeviceId", () -> {
                it("returns the 8 last characters of the device id", () -> {
                    final Device device = Device.builder().withDeviceId(TEST_DEVICE_ID).build();
                    final SystemState state = SystemState.builder().withDevice(device).build();
                    final Context context = Context.builder().withSystem(state).build();
                    final RequestEnvelope requestEnvelope = RequestEnvelope.builder()
                        .withContext(context)
                        .build();
                    assertThat(SpeechletRequestUtil.getShortDeviceId(requestEnvelope)).isEqualTo(SHORT_ID);
                });
            });

            describe("#getUserId", () -> {
                it("returns the user Id when it is contained in the request", () -> {
                    final User user = User.builder().withUserId(TEST_USER_ID).build();
                    final SystemState state = SystemState.builder().withUser(user).build();
                    final Context context = Context.builder().withSystem(state).build();
                    final RequestEnvelope requestEnvelope = RequestEnvelope.builder()
                        .withContext(context)
                        .build();
                    assertThat(SpeechletRequestUtil.getUserId(requestEnvelope)).isEqualTo(TEST_USER_ID);
                });
                it("throws an exeption when the request contains no device id", () -> {
                    final RequestEnvelope requestEnvelope = RequestEnvelope.builder().build();
                    assertThatExceptionOfType(AlexaSonicException.class)
                        .isThrownBy(() -> SpeechletRequestUtil.getUserId(requestEnvelope))
                        .withMessage(SpeechletRequestUtil.MESSAGE_NOUSERID);
                });
            });

            describe("#getShortUserId", () -> {
                it("returns the 8 last characters of the user id", () -> {
                    final User user = User.builder().withUserId(TEST_USER_ID).build();
                    final SystemState state = SystemState.builder().withUser(user).build();
                    final Context context = Context.builder().withSystem(state).build();
                    final RequestEnvelope requestEnvelope = RequestEnvelope.builder()
                        .withContext(context)
                        .build();
                    assertThat(SpeechletRequestUtil.getShortUserId(requestEnvelope)).isEqualTo(SHORT_ID);
                });
            });

            describe("#getLocale", () -> {
                it("returns english if the the request contains no locale", () -> {
                    final RequestEnvelope requestEnvelope = RequestEnvelope.builder()
                        .build();
                    final HandlerInput input = HandlerInput.builder()
                        .withRequestEnvelope(requestEnvelope)
                        .build();
                    assertThat(SpeechletRequestUtil.getLocale(input)).isEqualTo(Locale.ENGLISH);
                });

                it("returns the locale of the request if it is contained", () -> {
                    final Request request = IntentRequest.builder()
                        .withRequestId(TEST_REQUEST_ID)
                        .withLocale("fr-CA")
                        .build();
                    final RequestEnvelope requestEnvelope = RequestEnvelope.builder()
                        .withRequest(request)
                        .build();
                    final HandlerInput input = HandlerInput.builder()
                        .withRequestEnvelope(requestEnvelope)
                        .build();

                    assertThat(SpeechletRequestUtil.getLocale(input)).isEqualTo(Locale.CANADA_FRENCH);
                });
            });

            describe("#getAudioPlayerState", () -> {
                it("returns the audioplayer state of the request", () -> {
                    final AudioPlayerState state = createAudioPlayerState();
                    final Context context = Context.builder().withAudioPlayer(state).build();
                    final RequestEnvelope requestEnvelope = RequestEnvelope
                        .builder()
                        .withContext(context)
                        .build();

                    assertThat(SpeechletRequestUtil.getAudioPlayerState(requestEnvelope)).isEqualTo(Optional.of(state));
                });
                it("returns an empty audioplayer state when the request has none", () -> {
                    final RequestEnvelope requestEnvelope = RequestEnvelope.builder().build();
                    assertThat(SpeechletRequestUtil.getAudioPlayerState(requestEnvelope)).isEqualTo(Optional.empty());
                });
            });

            describe("#getAudioPlayerToken", () -> {
                it("returns the audioplayer token of the request", () -> {
                    final AudioPlayerState state = createAudioPlayerState();
                    final Context context = Context.builder().withAudioPlayer(state).build();
                    final RequestEnvelope requestEnvelope = RequestEnvelope
                        .builder()
                        .withContext(context)
                        .build();

                    assertThat(SpeechletRequestUtil.getAudioPlayerToken(requestEnvelope)).isEqualTo(state.getToken());
                });
                it("throws an exception when the request has no token (not playing)", () -> {
                    final RequestEnvelope requestEnvelope = RequestEnvelope.builder().build();
                    assertThatExceptionOfType(AlexaSonicException.class)
                        .isThrownBy(() -> SpeechletRequestUtil.getAudioPlayerToken(requestEnvelope))
                        .withMessage(SpeechletRequestUtil.MESSAGEKEY_NOT_PLAYING);
                });
            });

            describe("#getAudioPlayerOffsetInMilliseconds", () -> {
                it("returns the audioplayer offset of the request", () -> {
                    final AudioPlayerState state = createAudioPlayerState();
                    final Context context = Context.builder().withAudioPlayer(state).build();
                    final RequestEnvelope requestEnvelope = RequestEnvelope
                        .builder()
                        .withContext(context)
                        .build();

                    assertThat(SpeechletRequestUtil.getAudioPlayerOffsetInMilliseconds(requestEnvelope))
                        .isEqualTo(state.getOffsetInMilliseconds());
                });
                it("throws an exception when the request has no offset (not playing)", () -> {
                    final RequestEnvelope requestEnvelope = RequestEnvelope.builder().build();
                    assertThatExceptionOfType(AlexaSonicException.class)
                        .isThrownBy(() -> SpeechletRequestUtil.getAudioPlayerOffsetInMilliseconds(requestEnvelope))
                        .withMessage(SpeechletRequestUtil.MESSAGEKEY_NOT_PLAYING);
                });
            });

        });

    }

    private AudioPlayerState createAudioPlayerState() {
        return AudioPlayerState.builder()
            .withOffsetInMilliseconds(25L)
            .withPlayerActivity(PlayerActivity.BUFFER_UNDERRUN)
            .withToken("TOK")
            .build();
    }
}
