package ch.puzzle.ln.zeus.web.rest;

import ch.puzzle.ln.zeus.config.ApplicationProperties;
import ch.puzzle.ln.zeus.domain.enums.MacaroonClass;
import ch.puzzle.ln.zeus.service.LndService;
import io.micrometer.core.annotation.Timed;
import org.lightningj.lnd.wrapper.message.GetInfoResponse;
import org.lightningj.lnd.wrapper.message.ListChannelsResponse;
import org.lightningj.lnd.wrapper.message.NodeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/lnd")
@Timed
public class LndResource extends AbstractHealthIndicator {

    private static final Logger LOGGER = LoggerFactory.getLogger(LndResource.class);

    private final ApplicationProperties applicationProperties;
    private final LndService lndService;

    public LndResource(ApplicationProperties applicationProperties, LndService lndService) {
        this.applicationProperties = applicationProperties;
        this.lndService = lndService;
    }

    @GetMapping(value = "")
    public GetInfoResponse getInfo() throws Exception {
        return lndService.getInfo();
    }

    @GetMapping("/channels")
    public ListChannelsResponse getChannels() throws Exception {
        return lndService.getChannels();
    }

    @GetMapping("/nodeinfo/{nodeId}")
    public NodeInfo getNodeInfo(@PathVariable String nodeId) throws Exception {
        return lndService.getNodeInfo(nodeId);
    }

    @GetMapping("/permissions")
    public List<MacaroonClass> getAccreditedPermissions() {
        return applicationProperties.getLnd().getClassesOfProvidedMacaroon();
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        try {
            GetInfoResponse info = lndService.getInfo();

            builder
                .withDetail("blockHeight", info.getBlockHeight())
                .withDetail("blockHash", info.getBlockHash())
                .up();
        } catch (Exception e) {
            LOGGER.error("Error in LND health check.", e);
            builder.down(e);
        }
    }
}
