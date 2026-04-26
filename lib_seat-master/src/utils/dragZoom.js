// src/utils/dragZoom.js
export default {
  // 通用拖动初始化（区分画布/座位）
  initDrag({
    // isDragging,
    startX,
    startY,
    currentTarget,
    translateX = 0, // 画布偏移X（仅画布用）
    translateY = 0, // 画布偏移Y（仅画布用）
    top = 0,        // 座位top（仅座位用）
    left = 0,       // 座位left（仅座位用）
    scale           // 缩放比例
  }) {
    return {
      isDragging: true,
      startX,
      startY,
      prevTranslateX: translateX,
      prevTranslateY: translateY,
      prevTop: top,
      prevLeft: left,
      currentTarget,
      scale
    };
  },

  // 通用拖动计算（返回偏移量）
  calculateDrag({
    isDragging,
    startX,
    startY,
    clientX,
    clientY,
    prevTranslateX,
    prevTranslateY,
    prevTop,
    prevLeft,
    scale,
    type, // 'canvas' 或 'seat'
    canvasSize, // 画布尺寸 {width, height}（仅座位用）
    seatSize    // 座位尺寸 {width, height}（仅座位用）
  }) {
    if (!isDragging) return null;

    // 计算鼠标移动增量（抵消缩放比例）
    const deltaX = (clientX - startX) / scale;
    const deltaY = (clientY - startY) / scale;

    if (type === 'canvas') {
      // 画布拖动：直接返回新偏移
      return {
        translateX: prevTranslateX + deltaX,
        translateY: prevTranslateY + deltaY
      };
    } else if (type === 'seat') {
      // 座位拖动：计算新位置并限制在画布内
      let newLeft = prevLeft + deltaX;
      let newTop = prevTop + deltaY;

      // 边界限制：座位不能超出画布
      if (newLeft < 0) newLeft = 0;
      if (newTop < 0) newTop = 0;
      if (newLeft + seatSize.width > canvasSize.width) {
        newLeft = canvasSize.width - seatSize.width;
      }
      if (newTop + seatSize.height > canvasSize.height) {
        newTop = canvasSize.height - seatSize.height;
      }

      return { left: newLeft, top: newTop };
    }
  },

  // 停止拖动
  stopDrag(state, stopHandler) {
    // 移除所有相关事件监听器
    window.removeEventListener('mousemove', state.dragHandler);
    window.removeEventListener('mouseup', stopHandler);
    return {
      isDragging: false,
      currentTarget: null,
      dragHandler: null
    };
  },

  // 通用缩放逻辑
  calculateScale({ scale, deltaY, zoomStep, minScale, maxScale }) {
    if (deltaY > 0) {
      return Math.max(minScale, scale - zoomStep);
    } else {
      return Math.min(maxScale, scale + zoomStep);
    }
  }
};