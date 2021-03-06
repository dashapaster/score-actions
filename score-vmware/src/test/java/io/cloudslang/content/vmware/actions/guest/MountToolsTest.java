package io.cloudslang.content.vmware.actions.guest;

import io.cloudslang.content.vmware.entities.VmInputs;
import io.cloudslang.content.vmware.entities.http.HttpInputs;
import io.cloudslang.content.vmware.services.GuestService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * Created by Mihai Tusa.
 * 4/8/2016.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(MountTools.class)
public class MountToolsTest {
    private MountTools mountTools;

    @Before
    public void init() {
        mountTools = new MountTools();
    }

    @After
    public void tearDown() {
        mountTools = null;
    }

    @Mock
    private GuestService guestServiceMock;

    @Test
    public void MountToolsSuccess() throws Exception {
        Map<String, String> resultMap = new HashMap<>();
        whenNew(GuestService.class).withNoArguments().thenReturn(guestServiceMock);
        when(guestServiceMock.mountTools(any(HttpInputs.class), any(VmInputs.class))).thenReturn(resultMap);

        resultMap = mountTools.mountTools("", "", "", "", "", "", "");

        verify(guestServiceMock, times(1)).mountTools(any(HttpInputs.class), any(VmInputs.class));

        assertNotNull(resultMap);
    }

    @Test
    public void MountToolsProtocolFailure() throws Exception {
        Map<String, String> resultMap = new HashMap<>();
        whenNew(GuestService.class).withNoArguments().thenReturn(guestServiceMock);
        when(guestServiceMock.mountTools(any(HttpInputs.class), any(VmInputs.class))).thenReturn(resultMap);

        resultMap = mountTools.mountTools("", "", "myProtocol", "", "", "", "");

        verify(guestServiceMock, never()).mountTools(any(HttpInputs.class), any(VmInputs.class));

        assertNotNull(resultMap);
        assertEquals(-1, Integer.parseInt(resultMap.get("returnCode")));
        assertEquals("Unsupported protocol value: [myProtocol]. Valid values are: https, http.", resultMap.get("returnResult"));
    }
}
