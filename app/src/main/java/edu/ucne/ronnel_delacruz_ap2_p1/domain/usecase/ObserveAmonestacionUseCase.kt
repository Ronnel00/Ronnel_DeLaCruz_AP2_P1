package edu.ucne.ronnel_delacruz_ap2_p1.domain.usecase

import edu.ucne.ronnel_delacruz_ap2_p1.domain.repository.AmonestacionRepository
import javax.inject.Inject

class ObserveAmonestacionesUseCase @Inject constructor(
    private val repository: AmonestacionRepository
) {
    operator fun invoke() = repository.observeAmonestaciones()
}