package fuzs.eternalnether.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.eternalnether.EternalNether;
import fuzs.eternalnether.client.model.geom.ModModelLayers;
import fuzs.eternalnether.client.renderer.entity.state.WraitherRenderState;
import fuzs.eternalnether.world.entity.monster.Wraither;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.AbstractSkeletonRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;

public class WraitherRenderer extends AbstractSkeletonRenderer<Wraither, WraitherRenderState> {
    private static final ResourceLocation TEXTURE_LOCATION = EternalNether.id("textures/entity/skeleton/wraither.png");
    private static final ResourceLocation POSSESSED_TEXTURE_LOCATION = EternalNether.id(
            "textures/entity/skeleton/wraither_possessed.png");
    private static final RenderType EYES_RENDER_TYPE = RenderType.eyes(EternalNether.id(
            "textures/entity/skeleton/wraither_eyes.png"));

    public WraitherRenderer(EntityRendererProvider.Context context) {
        super(context,
                ModModelLayers.WRAITHER,
                ModModelLayers.WRAITHER_INNER_ARMOR,
                ModModelLayers.WRAITHER_OUTER_ARMOR);
        this.addLayer(new EyesLayer<>(this) {
            @Override
            public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, WraitherRenderState renderState, float yRot, float xRot) {
                if (renderState.isPossessed) {
                    super.render(poseStack, bufferSource, packedLight, renderState, yRot, xRot);
                }
            }

            @Override
            public RenderType renderType() {
                return EYES_RENDER_TYPE;
            }
        });
    }

    @Override
    public ResourceLocation getTextureLocation(WraitherRenderState renderState) {
        return renderState.isPossessed ? POSSESSED_TEXTURE_LOCATION : TEXTURE_LOCATION;
    }

    @Override
    public WraitherRenderState createRenderState() {
        return new WraitherRenderState();
    }

    @Override
    public void extractRenderState(Wraither wraither, WraitherRenderState renderState, float partialTick) {
        super.extractRenderState(wraither, renderState, partialTick);
        renderState.isPossessed = wraither.isPossessed();
    }
}
