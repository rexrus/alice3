/*******************************************************************************
 * Copyright (c) 2006, 2015, Carnegie Mellon University. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Products derived from the software may not be called "Alice", nor may
 *    "Alice" appear in their name, without prior written permission of
 *    Carnegie Mellon University.
 *
 * 4. All advertising materials mentioning features or use of this software must
 *    display the following acknowledgement: "This product includes software
 *    developed by Carnegie Mellon University"
 *
 * 5. The gallery of art assets and animations provided with this software is
 *    contributed by Electronic Arts Inc. and may be used for personal,
 *    non-commercial, and academic use only. Redistributions of any program
 *    source code that utilizes The Sims 2 Assets must also retain the copyright
 *    notice, list of conditions and the disclaimer contained in
 *    The Alice 3.0 Art Gallery License.
 *
 * DISCLAIMER:
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND.
 * ANY AND ALL EXPRESS, STATUTORY OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,  FITNESS FOR A
 * PARTICULAR PURPOSE, TITLE, AND NON-INFRINGEMENT ARE DISCLAIMED. IN NO EVENT
 * SHALL THE AUTHORS, COPYRIGHT OWNERS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, PUNITIVE OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING FROM OR OTHERWISE RELATING TO
 * THE USE OF OR OTHER DEALINGS WITH THE SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *******************************************************************************/
package edu.cmu.cs.dennisc.java.awt;

import edu.cmu.cs.dennisc.java.util.DStack;
import edu.cmu.cs.dennisc.java.util.InitializingIfAbsentMap;
import edu.cmu.cs.dennisc.java.util.Maps;
import edu.cmu.cs.dennisc.java.util.Stacks;
import edu.cmu.cs.dennisc.java.util.logging.Logger;

import javax.swing.SwingUtilities;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;

/**
 * @author Dennis Cosgrove
 */
public final class GraphicsContext {
  private static final GraphicsContext edtInstance = new GraphicsContext();
  private static final InitializingIfAbsentMap<Thread, GraphicsContext> map = Maps.newInitializingIfAbsentHashMap();

  public static GraphicsContext getInstanceAndPushGraphics(Graphics g) {
    GraphicsContext rv;
    if (SwingUtilities.isEventDispatchThread()) {
      rv = edtInstance;
    } else {
      rv = map.getInitializingIfAbsent(Thread.currentThread(), new InitializingIfAbsentMap.Initializer<Thread, GraphicsContext>() {
        @Override
        public GraphicsContext initialize(Thread key) {
          Logger.outln("note: creating graphics context on thread", key);
          return new GraphicsContext();
        }
      });
    }
    rv.pushAll((Graphics2D) g);
    return rv;
  }

  private static <T> T popTo(DStack<T> stack, int size) {
    assert stack.size() > size;
    T o = null;
    while (stack.size() > size) {
      o = stack.pop();
    }
    return o;
  }

  private class GraphicsAndStackSizes {
    private final Graphics2D graphics2d;
    private final int paintStackSize;
    private final int strokeStackSize;
    private final int fontStackSize;
    private final int clipStackSize;
    private final int transformStackSize;
    private final int antialiasingStackSize;
    private final int textAntialiasingStackSize;

    public GraphicsAndStackSizes(Graphics2D graphics2d) {
      this.graphics2d = graphics2d;
      this.paintStackSize = paintStack.size();
      this.strokeStackSize = strokeStack.size();
      this.fontStackSize = fontStack.size();
      this.clipStackSize = clipStack.size();
      this.transformStackSize = transformStack.size();
      this.antialiasingStackSize = antialiasingStack.size();
      this.textAntialiasingStackSize = textAntialiasingStack.size();
    }

    public void popAll() {
      if (paintStack.size() > this.paintStackSize) {
        this.graphics2d.setPaint(popTo(paintStack, this.paintStackSize));
      }
      if (strokeStack.size() > this.strokeStackSize) {
        this.graphics2d.setStroke(popTo(strokeStack, this.strokeStackSize));
      }
      if (fontStack.size() > this.fontStackSize) {
        this.graphics2d.setFont(popTo(fontStack, this.fontStackSize));
      }
      if (clipStack.size() > this.clipStackSize) {
        this.graphics2d.setClip(popTo(clipStack, this.clipStackSize));
      }
      if (transformStack.size() > this.transformStackSize) {
        this.graphics2d.setTransform(popTo(transformStack, this.transformStackSize));
      }
      if (antialiasingStack.size() > this.antialiasingStackSize) {
        this.graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, popTo(antialiasingStack, this.antialiasingStackSize));
      }
      if (textAntialiasingStack.size() > this.textAntialiasingStackSize) {
        this.graphics2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, popTo(textAntialiasingStack, this.textAntialiasingStackSize));
      }
    }
  }

  private final DStack<GraphicsAndStackSizes> mainStack = Stacks.newStack();

  private final DStack<Paint> paintStack = Stacks.newStack();
  private final DStack<Stroke> strokeStack = Stacks.newStack();
  private final DStack<Font> fontStack = Stacks.newStack();
  private final DStack<Shape> clipStack = Stacks.newStack();
  private final DStack<AffineTransform> transformStack = Stacks.newStack();

  private final DStack<Object> antialiasingStack = Stacks.newStack();
  private final DStack<Object> textAntialiasingStack = Stacks.newStack();

  public GraphicsContext() {
  }

  public void pushAll(Graphics2D g) {
    this.mainStack.push(new GraphicsAndStackSizes(g));
  }

  public void popAll() {
    GraphicsAndStackSizes graphicsAndStackSizes = this.mainStack.pop();
    graphicsAndStackSizes.popAll();
  }

  private Graphics2D getGraphics2d() {
    GraphicsAndStackSizes graphicsAndStackSizes = this.mainStack.peek();
    return graphicsAndStackSizes.graphics2d;
  }

  public void pushFont() {
    this.fontStack.push(this.getGraphics2d().getFont());
  }

  public void popFont() {
    this.getGraphics2d().setFont(this.fontStack.pop());
  }

  public void pushClip() {
    this.clipStack.push(this.getGraphics2d().getClip());
  }

  public void popClip() {
    this.getGraphics2d().setClip(this.clipStack.pop());
  }

  public void pushTransform() {
    this.transformStack.push(this.getGraphics2d().getTransform());
  }

  public void popTransform() {
    this.getGraphics2d().setTransform(this.transformStack.pop());
  }

  public void pushPaint() {
    this.paintStack.push(this.getGraphics2d().getPaint());
  }

  public void popPaint() {
    this.getGraphics2d().setPaint(this.paintStack.pop());
  }

  public void pushStroke() {
    this.strokeStack.push(this.getGraphics2d().getStroke());
  }

  public void popStroke() {
    this.getGraphics2d().setStroke(this.strokeStack.pop());
  }

  private static void pushAndSetRenderingHint(Graphics2D g2, DStack<Object> stack, RenderingHints.Key key, Object value) {
    stack.push(g2.getRenderingHint(key));
    g2.setRenderingHint(key, value);
  }

  private static void popRenderingHint(Graphics2D g2, DStack<Object> stack, RenderingHints.Key key) {
    g2.setRenderingHint(key, stack.pop());
  }

  public void pushAndSetAntialiasing(boolean b) {
    pushAndSetRenderingHint(this.getGraphics2d(), this.antialiasingStack, RenderingHints.KEY_ANTIALIASING, b ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
  }

  public void popAntialiasing() {
    popRenderingHint(this.getGraphics2d(), this.antialiasingStack, RenderingHints.KEY_ANTIALIASING);
  }

  public void pushAndSetTextAntialiasing(boolean b) {
    pushAndSetRenderingHint(this.getGraphics2d(), this.textAntialiasingStack, RenderingHints.KEY_TEXT_ANTIALIASING, b ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
  }

  public void popTextAntialiasing() {
    popRenderingHint(this.getGraphics2d(), this.textAntialiasingStack, RenderingHints.KEY_TEXT_ANTIALIASING);
  }
}
