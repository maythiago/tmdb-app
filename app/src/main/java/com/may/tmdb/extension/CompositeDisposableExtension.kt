package com.may.tmdb.extension

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

object CompositeDisposableExtension {
    operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
        this.add(disposable)
    }
}
