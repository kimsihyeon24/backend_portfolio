import { useState, useEffect } from 'react';

export const useDebounce = (value, delay) => {
  const [debouncedValue, setDebouncedValue] = useState(value);

  useEffect(() => {
    // delay 시간 후에 value를 업데이트하는 타이머 설정
    const handler = setTimeout(() => {
      setDebouncedValue(value);
    }, delay);

    // 사용자가 다시 타자를 치면 이전 타이머를 취소 (핵심!)
    return () => clearTimeout(handler);
  }, [value, delay]);

  return debouncedValue;
};