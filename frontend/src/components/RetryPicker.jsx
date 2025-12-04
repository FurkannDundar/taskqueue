function RetryPicker({ maxRetry, setMaxRetry }) {
  return (
    <div className="space-y-3">
      <label className="block text-sm font-semibold text-gray-700">
        Maksimum Tekrar Deneme
      </label>
      <select
        value={maxRetry}
        onChange={(event) => setMaxRetry(event.target.value)}
        className="w-full px-4 py-3 bg-white border-2 border-gray-300 rounded-lg shadow-sm
                     focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 
                     hover:border-gray-400 transition-all cursor-pointer
                     text-gray-700 font-medium"
      >
        <option value="" disabled>
          Select max retry...
        </option>
        <option value="0">0</option>
        <option value="1">1</option>
        <option value="2">2</option>
        <option value="3">3</option>
      </select>
    </div>
  );
}

export default RetryPicker;
